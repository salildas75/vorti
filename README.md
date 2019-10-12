# vorti
# Abstract
The main objective of this project is to develop an android application for all the Students
who need the accommodation where he/she are going to participate in the Admission Test.

Some feature will help them to monitor every useful thing as their requirements. My ap-
plication is providing some features like books, map which are effective for Students,

every application is connected through database, my database is designed to gather multi-
ple data in a specific place which very essential for the application to do action depending

on the values from database. Accommodation Share is a technological platform which
helps bring together room sharers (hosts) & room seekers (guests) in an accommodation
marketplace aimed at offering a memorable staying experience for both the concerned
parties. What makes us unique is the fact that we not only help connect hosts with guests
but also have a special algorithmic user preference matching which helps in finding the
most compatible palace.

# System Design
1.1 For Renter User:
• SignUp : When a user wants to register himself/herself as a renter, they need to
fill up the register form provided by the application. The information they will
provide must be original because it will be checked throw a government database to
confirm their roll number is valid. The mobile number they will give must be there
own. Because the system will send an otp to confirm the number. After singing up
the registration form admin will check the information given by the user and can
approve or unapprove the request.
• SignIn : The Renter who will give rent their seats, by giving phone number and
password can signin. But he/she must be a valid user. After Signing, renter will see
all the features provided by the application.
• Edit Profile : Sometime users need to edit their profile. So in this application users
can only change their e-mail address, password, name and profile picture.
• New Post : while servicing a customer one can not request this renter because of
unavailable seats. And after the ending of the service the seats are free and the
renter can serve a new user to request for the seats and by this feature renter can
add available seats number, address, price of the seat.
• Request : By this renter can view the customers request like how many days he/she
want to stay the total price etc. By this if the renter feel comfortable renter can
accept or cancel the customer request.
• Accept : Renter can accept the customer request by this feature. While accepting
the customer conform with their accommodation. Customer then dont need to find
accommodation.
• Cancel : Renter can cancel the request of user if renter want to cancel the customer
request. After canceling renter can accept others and user can also request for other
renter.

• Start : While Renter accept the request and the Customer starting sharing the ac-
commodation. In requesting feature it is indicated that when the user start to stay

and how many days user stay there.
• End : The ending time of the costumer in sharing accommodation and the payment
also have to close.

1.2 For Regular User:
• SignUp : When a user wants to register himself/herself as a student, they need
to fill up the register form provided by the application. The information they will
provide must be original because it will be checked throw a government database to
confirm their roll number is valid. The mobile number they will give must be there
own. Because the system will send an otp to confirm the number. After singing up
the registration form admin will check the information given by the user and can
approve or unapprove the request.
• SignIn : The student who wants to take a seats, reading books, have some idea
about universities, need suggestion etc . By giving phone number and password the
renter can signin. But he/she must be a valid user.
• Search: Customer can search available seats and destination. For moving into other
city one can search by city and seats available.

• Varsity : Can view the details of all University. The university can be public,
private or national. While the examination starts how many seats are available etc.
• Map : Can find the desire location. In which areas the renter located in.
• Booking : Can find the available accommodation and request the renter user.
• Library : Dont have to carry the book, by this feature they can get their needed
books here. For reading essential books while in outside of home one can read
books by Vorti without carrying books.

1.3 For Admin:
• SignIn : Admin can access the admin panel to use his/her user id and password
• University : Can view the details of all University. The university can be public,
private or national. And admin can add, delete and edit the university if it will need.
• Library : By this feature admin can add more books for the students. If he/she
want’s to delete the specific book if necessary.
• Users : Admin will moderate the user is valid or invalid, after that he/she has right
to approve or unapprove the user by this section.
• Accommodation : Admin will moderate the user posting accommodation. Because
of the accommodation quality and security are ok or not
