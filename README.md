<div align="center">
  <h1>Real-Time Weather Monitoring System</h1>
  </div>


<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
  * [Screenshots](#camera-screenshots)
  * [Technologies Used](#space_invader-technologies-used)
  * [Features](#dart-features)
  * [Environment Variables](#key-environment-variables)
- [Getting Started](#toolbox-getting-started)
  * [Prerequisites](#bangbang-prerequisites)
  * [Installation](#gear-installation)
- [Deployment](#deployment)
- [Usage](#eyes-usage)
- [Contributing](#wave-contributing)
- [License](#warning-license)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)


<!-- About the Project -->
## :star2: About the Project
The Real-Time Weather Monitoring System is a robust application designed to fetch, monitor, and analyze weather data for multiple cities in the world. The system utilizes the OpenWeatherMap API to retrieve real-time weather information and alerts users based on customizable temperature thresholds. It features an intuitive user interface built with Spring Boot, Thymeleaf, and Hibernate for seamless interaction and data management.

<!-- Screenshots -->
### :camera: Screenshots

<div align="center">
  <table>
    <tr>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729583080/Screenshot_2024-10-22_130122_cyn8wg.png" alt="screenshot" width="400"/></td>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729583080/Screenshot_2024-10-22_130144_dfsmjv.png" alt="screenshot" width="400"/></td>
    </tr>
    <tr>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729583079/Screenshot_2024-10-22_131251_o8znjg.png" alt="screenshot" width="400"/></td>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729583080/Screenshot_2024-10-22_131325_lvrb43.png" alt="screenshot" width="400"/></td>
    </tr>
  </table>
</div>

  
  
</div>


<!-- TechStack -->
### :space_invader: Technologies Used

<details>
  <summary>Backend</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a></li>
    <li><a href="https://hibernate.org/">Hibernate</a></li>
    <li><a href="https://spring.io/guides/gs/scheduling-tasks">Spring Scheduler </a></li>
  </ul>
</details>

<details>
  <summary>Frontend</summary>
  <ul>
    <li><a href="https://www.thymeleaf.org/">Thymeleaf</a></li>
    <li><a href="https://html.com/">HTML</a></li>
    <li><a href="https://www.w3.org/Style/CSS/Overview.en.html">CSS</a></li>
    <li><a href="https://www.javascript.com/">JavaScript</a></li>
  
  </ul>
</details>

<details>
<summary>Database</summary>
  <ul>
    <li><a href="https://www.mysql.com/">MySQL</a></li>
  </ul>
</details>
<details>
<summary>API Integration</summary>
  <ul>
    <li><a href="https://home.openweathermap.org/">OpenWeatherMap API for weather data</a></li>
  </ul>
</details>

<details>
<summary>Deployment</summary>
  <ul>
    <li><a href="https://aws.amazon.com/ec2/">AWS EC2</a></li>
  </ul>
</details>

<!-- Features -->
### :dart: **Features**

- **Real-Time Weather Data**: Fetch current weather data every 5 minutes for major metro cities in India.
  
- **Temperature Alerts**: Set personalized alerts for temperature thresholds (above/below) and receive email notifications.
  
- **Daily Weather Aggregation**: Store and analyze daily weather data, including average, maximum, and minimum temperatures.
  
- **Historical Data Tracking**: View historical weather data for specific cities with detailed reports.
  
- **User-Friendly Interface**: A very user-friendly web application interface for easy navigation and data visualization.

 <!-- Env Variables -->
### :key: Environment Variables

To run this project, you will need to add the following environment variables to your .env file or application.properties

`api_key`

`db_url`

`db_username`

`db_password`

`mail_port`

`mail_username`

`mail_password`

<!-- Getting Started -->
## 	:toolbox: Getting Started

<!-- Prerequisites -->
### :bangbang: Prerequisites

- Java 17
- Spring Boot 3.2.5
- MySQL
- Maven

<!-- Installation -->
### :gear: Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/abhi03241/Weather-App.git
   cd Weather-App
   ```
2. **Configure your application.properties for openweathermap api, database connection and notification alert**:
    ```bash
    api.key=${api_key} //Enter your openweathermap api key here
    
    //DB connection
    spring.datasource.url=${db_url} //Enter your database url (jdbc:mysql://localhost:3306/weather_app_db)
    spring.datasource.username=${db_username}  //Enter your database username
    spring.datasource.password=${db_password}  //Enter your database passward
    
    //Mail connection
    spring.mail.port=${mail_port}  //Enter your mail port (587)
    spring.mail.username=${mail_username}  //Enter your Email
    spring.mail.password=${mail_password}  //Enter your passkey
    ```
3. **Run the application**:
   ```bash
   mvn spring-boot:run
    ```
4. **Access the application in your browser at** http://localhost:8080/home

### :triangular_flag_on_post: Deployment

The application has been successfully deployed on an AWS EC2 instance, allowing for easy access and management of real-time weather data globally.

**Accessing the Deployed Application**
- You can access the live application at: http://13.201.204.129:8000/home

<!-- Usage -->
## :eyes: Usage
1. **Set Up Alerts**: Users can create alerts based on temperature thresholds and will receive notifications via email.

2. **View Historical Data**: Navigate to the historical data section to view past weather reports for your selected cities.

3. **Dashboard**: The main dashboard displays the current weather conditions and any active alerts.

<!-- Contributing -->
## :wave: Contributing
  <img src="https://contrib.rocks/image?repo=Louis3797/awesome-readme-template" />

Contributions are welcome! Please feel free to submit issues or pull requests.

<!-- License -->
## :warning: License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/Vineet1025/Weather-App/blob/master/LICENSE.md) file for details.


<!-- Contact -->
## :handshake: Contact

Abhishek Shukla

Project Link: [https://github.com/abhi03241/Weather-App](https://github.com/abhi03241/Weather-App)

<!-- Acknowledgments -->
## :gem: Acknowledgements

- OpenWeatherMap API for providing weather data.
- Spring Boot for an efficient backend framework.
