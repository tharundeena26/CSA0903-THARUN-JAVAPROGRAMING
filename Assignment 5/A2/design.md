# A2 – Don't Read What You Don't Need

## A2.1 Predict the cost
A 50 MiB file contains 52,428,800 bytes. A loop calling FileInputStream.read() once per byte makes approximately one read() call per byte (52,428,800 Java method calls); the runtime may internally buffer at the OS/device layer, but the Java loop still incurs per-byte call/branch overhead.

## A2.2 Buffered redesign
Use BufferedInputStream with an 8 KiB or 64 KiB buffer and read into a byte array. The number of bulk reads is approximately ceil(fileSize / bufferSize): for 8 KiB, about 6,400 reads for 50 MiB instead of 52.4 million single-byte reads. Count newline byte 0x0A while processing each returned chunk. Complexity remains O(file size), with O(buffer size) extra memory.

## A2.3 When buffering helps little
If the source already delivers complete large blocks (for example, an in-memory byte array or a network API with its own large buffer), wrapping another buffer may add copying and little benefit. For a tiny file, overhead can also outweigh any gain.

## Alternative: Files.readAllLines
It reads all lines into a List<String>. Time remains O(file size), but memory grows O(file size) plus per-string/list overhead. Use it for small files that comfortably fit in memory and need repeated random access; use streaming for 50 MB or unbounded logs to keep memory O(buffer size).
