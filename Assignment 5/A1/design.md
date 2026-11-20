# A1 – Design a File Format: Transport Office Bus-Pass Records

## A1.1 Record layouts
**Delimited text layout (UTF-8, one logical record per line):**
`passId|regNo|name|routeNo|boardingStop|validUntil|amountPaid|checksum`
- Field order: ID, register number, name, route, boarding stop, ISO date (yyyy-MM-dd), decimal amount, checksum.
- Escape backslash as `\\` and pipe as `\|`; reject embedded CR/LF or encode them as escaped sequences. A parser must split on unescaped pipes, not a naive split.
- Checksum is SHA-256 over the canonical first seven fields; it detects accidental corruption, not malicious tampering.

**Binary DataOutputStream layout:**
- UTF passId, UTF regNo, UTF name, int routeNo, UTF boardingStop, long validityEpochDay, double amountPaid, int recordVersion.
- DataOutputStream.writeUTF stores a length prefix and modified UTF-8. Each variable string has a bounded encoded length; validate lengths and values before writing.
- For a truly fixed-size random-access design, reserve fixed byte lengths per field and document UTF-8 padding/truncation rules; the above DataOutputStream design is variable-length.

## A1.2 Operation pseudocode
**ADD(pass):** validate every field; serialize a canonical line; write to a temporary file or append under a file lock; flush and close; for atomic replacement write complete new content to a sibling temp file then move with ATOMIC_MOVE where supported.

**FIND(passId):** open BufferedReader(new FileReader(path)); read one line at a time; parse escaped delimiters; verify field count, field types and checksum; skip and log invalid records; return the matching pass ID or NOT_FOUND.

**LIST_BY_ROUTE(route):** use BufferedReader in try-with-resources; parse and validate each line; append valid records whose route matches; report damaged-line count separately.

BufferedReader/BufferedWriter reduce character-level I/O calls and provide line-oriented processing. FileReader/FileWriter are convenient but use the platform default charset; production code should use Files.newBufferedReader/newBufferedWriter with StandardCharsets.UTF_8. FileInputStream/FileOutputStream are appropriate for raw bytes or binary records, not convenient for delimited text.

## A1.3 Damaged data
- Damaged line: validate field count, checksum, numeric route/amount and date; log line number and reason; skip the record rather than aborting the full scan.
- Missing file: distinguish Files.notExists from permission/I/O errors; create it for first-time add, but return a clear empty-store result for find/list.
- Incomplete write/power loss: write replacement data to a temporary file, flush and force the file channel where required, then atomically rename; maintain a backup/journal and validate checksums on recovery.

## Alternative: fixed-length RandomAccessFile
If records have fixed recordSize and a known index, record i can be read with seek(i * recordSize), O(1). Searching by ID without an index still scans O(n). Fixed-width strings waste padding space and need strict maximum lengths; an ID-to-record-offset index makes lookup fast but must be kept consistent.
