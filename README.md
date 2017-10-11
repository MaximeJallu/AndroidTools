# Android : Recycler Tools

- SwitchReplayFirst.java
```java
private static class Sample {
  
  private Observable<String> getData(){
    return Observable.just("Sample");
  }
  
  private Observable<Void> putData(){
    return Observable.empty();
  }
  
  private void test(){
    getData()
      .compose(SwitchReplayFirst.switchReplayFirst(putData()))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnNext(value -> Log.d("TEST", value))
      .subscribe();
  }
}
```
