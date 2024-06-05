# Photography Alarm

A simple project to create an app which gets the user's location, then checks what time sunrise is for that location, and then correlates it to the weather forecast to suggest to the user to set an alarm half an hour before that in order to wake up and take nice photos of the sunrise, if the weather allows it.

The app was also a test to see how easy it would be to write the app quickly and simply, without using a very complex architecture, dependency injection etc.

Also added a setting for sunsets, since it was a request from a friend.

## Usage

Ensure your local.properties file contains an API key from [OpenWeatherMap](https://openweathermap.org/appid), like this:

```
weather.api.key=[your key here]
```
