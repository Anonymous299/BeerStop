# BeerStop
This is an application which helps users find the best value LCBO drinks based on price per alcohol volume.

### Technical decisions
#### Navigation
The goal of creating a Navigation system was to support Compose while keeping the app modular. I wanted to keep the implementation details of individual features
within their seperate modules which couldn't be done with the default Android provided NavHost. Nested graphs seems like a solution but still kind of keeps things highly coupled
with the base NavHost. Also adding a route requires changing at least two modules with that approach. So I opted for a solution given [here](https://medium.com/bumble-tech/scalable-jetpack-compose-navigation-9c0659f7c912).
As far as I can see it is a scalable solution that leverages Hilt to reduce coupling and isolates implementation details to individual modules.
For sending navigation commands, I used a navigation manager which executes the actual navigation command in the top-level Activity. This ensures a
single source of truth for navigation. A command can be sent through a buffered channel which is part of the manager. The command gets sent immediately without delay. The reason I used a Channel was to handle multiple calls to the navigate command in the form of a stream.

#### Steps to add new screen
* Create a new Android module with the name of the feature you want to build
* Create screen elements in composable functions, preferably within a package called presentation
* Create a navigation package. Add a class annotated with HiltComposeNavigationFactory inheriting from ComposeNavigationFactory. Override the create method and call your top compose function.  
  
These steps should add your screen to the navigation graph automatically.
