# mvp-sandbox
Android reactive MVP stack

The app uses a mock API (http://docs.crackywacky.apiary.io/) to mimic GitHub functionality. There's a custom RxJava implementation which can be found in feature/custom-rx branch.

Application is divided in three packages (according android clean architecture pattern http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/):
app - base feature, containing MainActivity and app start logic
core - base architecture framework to be moved to separate module or even plug with gradle dependency
repos - common feature. According to clean architecture every feature is stored in it's own package

NB since this this app is using MVP pattern term view in documentation is relates to V
in MVP (Model, View, Presenter).

This application has two dagger2 scopes. One is common scope which is implemented by creating component in Application.class.
And the other one is handled manually by creating and destroying components for current view via ComponentCache.


Service in terms of this application is a data handler. There's two base classes:
BaseService interface - makes nested classes implement all the logic
AbstractBaseService - extends BaseService and implements async save methods by wrapping sync save methods in Observables

By implementing BaseService (or extending AbstractBaseService) you can add your own data provider (f.e. in memory cache)
to access it via injection it have to be registered in SandboxModule
