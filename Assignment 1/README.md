# Assignment 1 – Campus Smart-Parking System

**Student:** THARUN D  
**Register number:** 192524399  
**Java version:** OpenJDK 21

## How to use this repository

Each task folder contains its Java source code and sample output capture. All 12 Java source files have been reformatted into normal, readable, line-by-line Java code. The earlier compressed one-line source formatting has been replaced. Step-by-step comments explain the main logic.

### Compile and run

Open a terminal in the relevant task folder. Use the file name and public class name shown below.

| Task | File | Compile | Run |
|---|---|---|---|
| 1 | Task01.java | `javac Task01.java` | `java Task01` |
| 2 | Task02.java | `javac Task02.java` | `java Task02` |
| 3 | ParkingFacility.java | `javac ParkingFacility.java` | `java ParkingFacility` |
| 4 | Task04.java | `javac Task04.java` | `java Task04` |
| 5 | Task05.java | `javac Task05.java` | `java Task05` |
| 6 | Task06.java | `javac Task06.java` | `java Task06` |
| 7 | Task07.java | `javac Task07.java` | `java Task07` |
| 8 | FeeDemo.java | `javac FeeDemo.java` | `java FeeDemo` |
| 9 | Task09.java | `javac Task09.java` | `java Task09` |
| 10 | Task10.java | `javac Task10.java` | `java Task10` |
| 11 | Task11.java | `javac Task11.java` | `java Task11` |
| 12 | Task12.java | `javac Task12.java` | `java Task12` |

## Step-by-step task guide

1. **Task 1 – Problem analysis and objects:** Create a Vehicle object, create a ParkingSlot object, try to park vehicles, and release the slot. The second vehicle is rejected while the slot is occupied.
2. **Task 2 – Data representation:** Use a jagged two-dimensional array because each floor can have a different number of slots. Store a registration number, then calculate started billing hours using `(minutes + 59) / 60`.
3. **Task 3 – Control logic:** Build a facility from a list of floor capacities. Scan floors and slots until a free slot is found, return its coordinates, and safely release a vehicle.
4. **Task 4 – Constructors:** Create a Ticket with overloaded constructors and constructor chaining. Validate the values, then create a copy using the copy constructor.
5. **Task 5 – Static members:** Use one static ticket counter and active-ticket count shared by all Ticket objects. Create tickets, close one, and observe the shared count.
6. **Task 6 – Inheritance:** Create an ElectricCar. Java first runs the Vehicle constructor, then Car, then ElectricCar. The child object reuses the inherited rate and adds charging behaviour.
7. **Task 7 – Method overriding:** Store Bike and Car objects in Vehicle references. The overridden rate method supplies the correct fee for each actual object.
8. **Task 8 – Polymorphism:** Store different vehicle types in a Vehicle array. Call the same fee method on each object; runtime dispatch selects the correct rate. ElectricCar also implements Chargeable.
9. **Task 9 – Abstraction and interfaces:** Define contracts for charging and payment. The EV implements both contracts and provides the required methods.
10. **Task 10 – Encapsulation:** Keep slot fields private. Read values through getters and change occupancy only through validated methods.
11. **Task 11 – Implementation and testing:** Use a jagged slot array and a HashMap of active tickets. Test successful entry, full capacity, fee calculation, exit, and invalid vehicle types.
12. **Task 12 – Adaptable design:** Use separate pricing strategies for hourly, flat-fee, and delivery-van pricing. Configure unequal floor capacities and apply a pass discount through the billing method.

## Important notes

- Run each task from its own folder so generated `.class` files stay separate.
- The output files are sample execution captures. Run the Java programs yourself and compare the terminal output before submission.
- The rates and registration numbers are illustrative assignment data. Adjust them only if your faculty specifies different values.
- Task 12 demonstrates a simplified delivery-van time-window rule. A production implementation should split parking duration at the exact 9:00 AM and 5:00 PM boundaries.
