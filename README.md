# Github App

## Tech stack
- 100% testable Kotlin code
- Dagger Hilt
- Room
- MVVM Architechture
- Using Mock server for testing remote data source
- Using Repository Pattern for Data Layer https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754

## Testing Strategy
### *Data Layer*
The data layer is tested through a combination of unit tests + Mockwebserver in order to fully test Retrofit network integrations. By following  this approach we are covering:
-  Retrofit instance
-  Json converters, in this case Kotlin Serialization converters
- Network data sources

### *Domain Layer*
For the domain layer the tests are standard unit tests focusing on outcome not implementation and using the correct test double for each case.

### *Presentation Layer*
Make sure flow ui tested as expected by using android ui test (espresso)