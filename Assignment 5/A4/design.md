# A4 – What Should Be Serialized?

## A4.1 Fields to persist
Serialize stable domain state: Hostel -> rooms -> residents and appropriate IDs/attributes. Mark Resident.password transient (credentials should not be persisted in a Java object graph; store a salted password hash in a secure credential store instead) and Connection transient (a live JDBC connection is a runtime resource and is not serializable/reusable). If the reference is not serializable, serialization otherwise fails with NotSerializableException. Mark caches, loggers, sockets and other runtime resources transient too. The reachable non-transient object graph is traversed recursively; every reachable object must be serializable.

## A4.2 Versioning
Declare an explicit serialVersionUID. When a new phoneNumber field is added while keeping the same compatible serialVersionUID, an older stream can usually be read and the new field receives its default value (null for String); use readObject/readResolve or migration logic to initialize it. An incompatible class change or mismatched serialVersionUID can cause InvalidClassException. Java serialization is version-sensitive and should not be used as an untrusted input format.

## Alternative: CSV/JSON
Text formats are generally more portable and easier to inspect; they need explicit schema/version and escaping rules. Save/load remains O(n) in resident count, with serialization often compact/fast for Java object graphs but tightly coupled to class structure. CSV/JSON can evolve through optional fields/defaults and explicit migrations. Exact file size and speed depend on data, encoding and library; measure rather than assume.
