# Library Management Android App

## Introduction
This project is an Android application developed using Android Studio that can be used by users/admin to perform various Library related operations such as searching books, seeing books issued to the user, re-issuing books, adding/removing/updating book, collecting fines etc. The App uses Google Firebase Cloud Firestore as the back-end database for storing details of books and users. Cloud Messaging is used to send notifications to users if a return deadline is approaching/fine is increased and when a new book is added to Library by an admin. Cloud Functions are used to monitor the database and update fines, trigger notifications. The App has a user-friendly interactive interface and is easy to use.

## Features
* Simple and minimal Layout Designs.
* Interlinked Activities for different functions.
* Text Views and Toasts for displaying info.
* Interaction with the User by help of Edit Text Views, Buttons, Checkboxes, Alert Dialogs, Card Views etc.
* Updates using Push Notifications.
* Realtime Synchronization with Online Database.
* Auto Login on App launch if the user/admin is logged in.
* Security Rules to protect database from malicious activities.

## Functionalities
* Admin Account :

  * Add new Book to the Library.
  * Update details of an existing Book.
  * Remove a Book from the Library.
  * Issue a Book to a User.
  * Return a Book from User.
  * Re-Issue a Book to a User.
  * Collect Fine from a User.
  * Search for a particular Book.
  
* User Account :

  * Search for a particular Book.
  * See Books issued to him.
  * Re-issue a Book one time.
  
* Push Notifications to Users when :

  * New Book added to the Library.
  * Fine of the User increases.
  * Deadline for a particular Book is nearby (3 days).
  
* Cloud Functions to :

  * Increase Fine of user if deadline is crossed once in a day.
  * Trigger Notifications based on events.

## Screenshots

|![](Screenshots/Log_In_Page.png)|![](Screenshots/User_Registration_Page.png)|![](Screenshots/Admin_Registration_Page.png)|
|:---:|:---:|:---:|
|**Log In Page**|**User Registration Page**|**Admin Registration Page**|

|![](Screenshots/User_Home_Page.png)|![](Screenshots/Admin_Home_Page.png)|![](Screenshots/Add_Book_Page.png)|
|:---:|:---:|:---:|
|**User Home Page**|**Admin Home Page**|**Add Book Page**|

|![](Screenshots/Remove_Book_Page.png)|![](Screenshots/Update_Book_Page.png)|![](Screenshots/Issue_Book_Page.png)|
|:---:|:---:|:---:|
|**Remove Book Page**|**Update Book Page**|**Issue Book Page**|

|![](Screenshots/Return_Book_Page.png)|![](Screenshots/Reissue_Book_Page.png)|![](Screenshots/Collect_Fine_Page.png)|
|:---:|:---:|:---:|
|**Return Book Page**|**Reissue Book Page**|**Collect Fine Page**|

|![](Screenshots/Collect_Fine_Confirmation_Page.png)|![](Screenshots/Search_Book_Page.png)|![](Screenshots/Search_Book_Results.png)|
|:---:|:---:|:---:|
|**Collect Fine Confirmation**|**Search Book Page**|**Search Book Results**|

|![](Screenshots/See_Issued_Books_Page.png)|![](Screenshots/User_Reissue_Book_Page.png)|![](Screenshots/New_Book_Added_Notification.png)|
|:---:|:---:|:---:|
|**See Issued Books Page**|**User Reissue Book Page**|**New Book Added Notification**|

|<img src=Screenshots/Deadline_Approaching_Notification.png width="267">|<img src=Screenshots/Fine_Increased_Notification.png width="267">|
|:---:|:---:|
|**Deadline Approaching Notification**|**Fine Increased Notification**|

## Tools Used
* [Android Studio](https://developer.android.com/studio)
* [Cloud Firestore](https://firebase.google.com/products/firestore)
* [Cloud Functions](https://cloud.google.com/functions)
* [Firebase Cloud Messaging](https://firebase.google.com/products/cloud-messaging)

## License
This Project is licensed under the MIT License.

## Final Notes
**Thanks for Reading !** You are welcome to contribute :

1. Fork it !
2. Create new branch : `git checkout -b my-new-branch`
3. Commit your changes : `git commit -am 'Added some changes'`
4. Push to the branch : `git push origin my-new-branch`
5. Submit a pull request !
