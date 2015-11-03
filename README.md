# mvp-sandbox
Android reactive MVP stack

The app uses a mock API (http://docs.crackywacky.apiary.io/) to mimic GitHub functionality. There's a custom RxJava implementation which can be found in feature/custom-rx branch.

Application is divided in three main packages:
app -
core - base architecture framework to be moved to separate module or even plug with gradle dependency
repos -

NB since this this app is using MVP pattern term view in documentation is relates to V
in MVP (Model, View, Presenter).

This application has two dagger2 scopes. One is common scope realized by creating component in Application.class.
And the other one is handled manually by creating and destroying components for current view via ComponentCache.