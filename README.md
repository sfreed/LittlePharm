Requirements: 

We can send and receive drug information in YAML or JSON format
Drug information sent to us can have multiple records included
Drug information is sent via thousands of manufacturers
Drug information is received throughout the day but typically comes in at 12 am UTC.
We store five attributes for drugs: 
UID (GUID)
Manufacturer 
Name
Quantity
Price
Make sure to document any assumptions you make. If you get to a stopping point before completing everything you would have liked, document what you would do with more time, what other features you would add, etc. The aim is not to write a comprehensive application but to show us your vision. Feel free to ask any clarifying questions before you get started.


Your submission must include instructions on how to build and execute your application.

Submission of Exercise:

Return the completed exercise within the next 7 days. Upon completion of the exercise, please send your project files as a zip file to sczadzeck@surecost.com. If you're unable to attach your project files directly as a zip file, you can submit the exercise by uploading the files to a cloud storage service like Google Drive, OneDrive, or iCloud. After uploading, adjust the sharing settings to allow access, then include the link to the files in your submission. This ensures that the recipient can easily access and review your project without attachment issues.

Additional Notes:

While the application backend should be written in Java, feel free to utilize any additional frameworks or tools you feel comfortable with such as Spring, Hibernate, Docker, etc.
Please include a couple of unit tests that work, but feel free to stub more tests out with appropriate names to show what else you'd like to see tested.
SPRING BOOT Application

Assumptions
1. Pharmaceutical has 5 fields(Uid, Name, Quantity, Price, Manufacturer);
2. Manufacturer has 2 fields (Uid, Name)
3. All New or Updated Pharmaceuticals will be sent from a Manufacturer that is known. Items from unknown Manufacturer will be rejected.
4. If Manufacturer is not known, a Manufacturer record MUST be created first and Manufacturer must be added to Pharmaceutical record.
