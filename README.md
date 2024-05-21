# User Track

This is a Spring Boot application that provides a RESTful API for managing users and their associated managers. The application allows creating, retrieving, updating, and deleting users, as well as performing bulk updates on users' managers.

## Features

- Create a new user with a full name, mobile number, PAN number, and associated manager.
- Retrieve users based on their ID, mobile number, or manager ID.
- Delete users by their ID or mobile number.
- Update a single user's information, including their full name, mobile number, PAN number, and manager.
- Perform bulk updates to assign multiple users to a new manager.
- Custom validation for mobile numbers and PAN numbers.
- Exception handling with descriptive error messages.
- Persistence with an H2 in-memory database.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database
- Lombok
- Jakarta Validation

## Getting Started

To run the application locally, follow these steps:

1. Clone the repository: `git clone https://github.com/your-repo/user-management.git`
2. Navigate to the project directory: `cd user-management`
3. Build the project: `./mvnw clean install`
4. Run the application: `./mvnw spring-boot:run`

The application will start running on `http://localhost:8080`.

## API Endpoints


## POST /create_user

###### Request Body:

```json
{
    "fullName": "John Doe",
    "mobNum": "1234567890",
    "panNum": "ABCDE1234F",
    "managerId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
}
```

##  POST /get_users

###### Request Parameters:

- userId: UUID
- mobNum: String
- managerId: UUID


## POST /delete_user
```json
{
"user_id": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
}
```
OR
```json
{
    "mob_num": "1234567890"
}
```

## POST /update_user

###### Request Body (Single Update):
```json
{
    "userId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
    "updateData": {
        "full_name": "Jane Doe",
        "mob_num": "9876543210",
        "pan_num": "ZYXWV9876R",
        "manager_id": "yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy"
    }
}
```

###### Request Body (Bulk Update):

```json
{
    "userIds": [
        "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
        "yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy"
    ],
    "updateData": {
        "manager_id": "zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz"
    }
}
```




