# BeerStop
This is an application which helps users find the best value LCBO drinks based on price per alcohol volume.

### Technical decisions
#### Navigation
The goal of creating a Navigation system was to support Compose while keeping the app modular. I wanted to keep the implementation details of individual features
within their seperate modules which couldn't be done with the default Android provided NavHost. Nested graphs seems like a solution but still kind of keeps things highly coupled
with the base NavHost. Also adding a route requires changing at least two modules with that approach. So I opted for a solution given [here](https://medium.com/bumble-tech/scalable-jetpack-compose-navigation-9c0659f7c912).
As far as I can see it is a scalable solution that leverages Hilt to reduce coupling and isolates implementation details to individual modules.
