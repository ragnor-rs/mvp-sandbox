# mvp-sandbox
Android sample app based on Visum (https://github.com/ragnor-rs/visum)

There's a custom RxJava implementation which can be found in feature/custom-rx branch.

The backend is located at https://safe-reaches-4393.herokuapp.com/

Backend sources: https://github.com/ragnor-rs/mvp-sandbox-backend

## Model

Let's discuss a single feature, to clear up, how visual framework works.

Large part of every MVP application is Model.
Visum helps (but not enforces) you to build your model layer with [StorIo](https://github.com/pushtorefresh/storio) and [Retrofit](http://square.github.io/retrofit/) libraries [reactive way](https://github.com/ReactiveX/RxJava). 
And it is covered by current example.

### [BaseService](https://github.com/ragnor-rs/visum/blob/develop/src%2Fmain%2Fjava%2Fio%2Freist%2Fvisum%2Fmodel%2FBaseService.java) provides convinient [CRUD](https://www.wikiwand.com/en/Create,_read,_update_and_delete) interface.

RetrofitRepoService
