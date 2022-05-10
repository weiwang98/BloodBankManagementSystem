# My Personal Project

## :syringe: Blood Donation System 

### :busts_in_silhouette: Three Characters:
- Blood Bank Manager :bowtie:
- Blood Donor :muscle:
- Patient :mask:

### :memo: Description
This project is a smart *blood donation* :syringe: management system. It is designed for the blood service providers
, aiming to manage the information of blood donors, manage the blood in the inventory, and distribute blood to patients 
in need systematically.

The project is based on **java** language. It is only available on **desktops** :computer:. 

### :sparkles: Inspiration 
I've been a Red Cross volunteer for 3 years in China, and I am a blood donor. Blood can save lives, and I hope to design 
a software to make the donation process more efficient. 

### :ocean: User Stories 
- As a user, I want to be able to choose a character from **blood bank manager:bowtie:**, **blood donor:muscle:**
, and **patient :mask:**.
- As a user, I want to be able to test whether a donor is **eligible (age>=17 & weight>=50)**.  
- As a user, I want to be able to generate a **blood donor card :credit_card:** for every **donor**. 
- As a user, I want to be able to **add** the donated blood in the **blood bank** :bank:.
- As a user, I want to be able to **store** a **donor list** :page_with_curl: with all the donors' names 
and corresponding blood types on it. 
- As a user, I want to be able to **distribute** blood to patients in **urgent** need first. 
- As a user, I want to be able to **add** a donor to the **donor list :page_with_curl:**.
- As a user, I want to be able to **distribute** blood to patients according to their **blood type**:
  - Patient with Type :a: blood can receive Type :a: or :o2: blood. 
  - Patient with Type :b: blood can receive Type :b: or :o2: blood.
  - Patient with Type :ab: blood can receive Type :ab:, :a:, :b: or :o2: blood.
  - Patient with Type :o2: blood can only receive Type :o2: blood.
    - **WE NEED MORE TYPE O DONORS!!!!!!!!!!!!!!! :hushed:**
- As a user, I want to be able to **add** a patient to the **wait list** :page_with_curl:,
with their names, blood types, and required amounts on it. 
- As a user, I want to be able to use a **password** (:key:**: trustnaturalrecursion**) 
to verify the identity of the **manager** .
- As a user, I want to be able to show the **manager** the **overall** information of the **blood bank**:
  - Total amount of :a:
  - Total amount of :b:
  - Total amount of :ab:
  - Total amount of :o2:
  - Donor list :memo:
  - Normal patient wait list :page_with_curl:
  - Urgent patient wait list :notebook:
- As a user, when I select the quit option from the main menu, I want to be reminded to save my bloodbank information
  (total amount of blood, donor list, patient wait list) to file and have the option to do so or not.
- As a user, when I start the application, I want to be give the option to resume exactly where I left off last time.
- As a user, I want to be able to delete a donor from the donor list, and delete a patient from the patient wait list.

##Phase 4 - Task 2
- When a donor donates blood, it will be logged as "A donor donated blood."
- When a patient tries to use blood, it will be logged as "A patient tried to used blood."
- When a manager deletes a donor from the donor list, it will be logged as "A donor was deleted from the donor list."
- When a manager deletes a patient from the wait list, it will be logged as "A patient was deleted from the wait list."

##Phase 4 - Task 3
- Ways to optimize the project: 
  - Creates a "human" interface for BloodDonor and Patient classes to implement.

###References: 
- TellerApp by Felix Grund - https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
- JsonSerializationDemo by Paul Carter - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
