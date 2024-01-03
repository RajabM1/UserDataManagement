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


![](https://up6.cc/2024/01/170431527894821.png)