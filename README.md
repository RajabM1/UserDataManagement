# UserDataManagement


# Noor
- **[Part1]** : **Compress Pdf Files**
- **[Part2]** : **Download [.zip] file directly** & **Upload [.zip] file to Drive**
## Part1
Depends on requirement there is a compression process, in our case **[compress to .zip]**, so i start thinking to implement in a way that be easy to add in future without editing on the existing code.
So I'm thinking about compressing extensions as **[Strategies]** because the process of compressing is one but in different way and this is like **[Strategy Design Pattern]** => **[Same behaviour different implementation]**
- First create **[ICompressStrategy]** interface.
- Second create **[ZipCompressedStrategy]** implements **[ICompressStrategy]**
- Third create **[Compressor]** => this is the context class
- Four I decide to made **[Compressor]** => **[Singleton Design Pattern]** at same time to less the object creation
## Part2
while the process of the exporting user data following same steps but the way of download are different we used **[Template Design Pattern]**
- First create an abstract class **[DataExportingTemplate]**
- Second create the options => **[DownloadDirectOption]** & **[UploadToDriveOption]**

I think these implementation is good because it follows the **[Solid Design Principles]** where :
- code depends on abstractions and this achieve **[Dependency Inversion Principle (DIP)]**
- each class responsible on one thing to do and this achieve **[Single Responsibility Principle (SRP)]**
- code allows for extension without modifying existing code and this achieve **[Open/Closed Principle (OCP)]**


# Rajab

## Document Generation
In this features the system should generate different pdf file depend on user type each user has an own files
Depend on the requirements there is a several services each service has a different algorithm but the process is general
for each service because of that i decide to implement a Strategy design pattern with Factory to decide which service to
generate depend on user type

First I implement a **[IDocumentGeneration]** interface to allow different implementation without changing existing code by
apply it lead to **[open/close principle]**

then I implement **[AbstractPdfDocumentGeneration]** this class has three methods:
- getFileName() this method return file name
- addContent() this is an abstract method this methods implement in the concrete class to avoid code duplication
- generateDocument() this method has a structure to generate pdf to avoid code duplication

in the concrete class each class added his own data

after that I add a context class **[Generator]** to decide which service to call

after that the **[DocumentGenerationFactory]** to create appropriate context depend on user type

finally as noor said above we decide to use template design pattern because Exporting data process depend on following steps
Document Generation & compressing files





# Dana

Depends on the requirements there are two types of deletion which are soft and hard delete ,
while the process of delete is same to document generation because it depends on user type .
I made this feature implementation like document generation implementation , so i used strategy design pattern and factory .

First I created an **[IDelete]** interface to allow different implementation without changing existing code by
apply it lead to **[open/close principle]**

then I implement abstract class **[BaseDataDelete]**

I created 4 classes: **[UserActivityDelete]** , **[UserDataDelete]** , **[UserPaymentDelete]**, and **[UserPostDelete]**
to be able each class has its own implementation .

then i made **[DataDeletionContext]** to call deletion strategies
and i implement **[DataDeletionFactory]** to create data deletion context depend on user type .



*note* the class diagrams are in folder class_diagrams 





![](https://up6.cc/2024/01/170431527894821.png)







