import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class RxBus() {

    companion object {
        private val publisher: PublishSubject<String> = PublishSubject.create()

        private var mInstance: RxBus? = null
        val instance: RxBus?
            get() {
                if (mInstance == null) {
                    mInstance = RxBus()
                }
                return mInstance
            }
        fun publish(event: String?) {
            publisher.onNext(event)
        }

        fun listen(): Observable<String> {
            return publisher
        }
    }
}
