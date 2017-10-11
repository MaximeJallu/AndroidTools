private static class SwitchReplayFirst<T, U> implements Observables.Transformer<T, T> {
  private AtomicReference<T> mReference;
  private Observable<U> mNext;
  
  public static <T, U> Observable.Transformer<T, T> switchReplayFirst(Observable<U> nextObservable) {
    return new SwitchReplayFirst<>(nextObservble);
  }
  
  private SwitchReplayFirst(Observable<U> next) {
    mReference = new AtomicReference<>(null);
    mNext = next;
  }
  
  @Override
  public Observable<T> call(Observable<T> observable) {
    return observable.doOnNext(value -> mReference.set(value))
      .flapMap(obs -> mNext)
      .map(value -> mReference.get());
  }
}
