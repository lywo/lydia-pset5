# Weather app README #

## Description ##
App that gets user input city name and via http request, gets current weather data from that city.
The weather API from openweathermap.org is used for data.
Different cities can be stored in a ListView, with information about rain, temperature, clouds and wind speed.
Data is not saved when app is closed, so a new search gives up to date weather information. 
It is possible to save your favourite cities, whith just a short-press. 
Every time the app is started, you favourites are downloaded from a database, and automatically a html request for each of the favourites is send. 
Delete an item from ListView and Database via longpress on the item in the ListView. 

## Screenshots ##
![List of Cities](https://github.com/lywo/lydia-pset5/blob/master/doc/screenshotcitieslang-resize.png?raw=true)

