
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class NewsBus() {
    companion object {
        private var readed = arrayListOf<String>()
        private val publisher: PublishSubject<String> = PublishSubject.create()
        private var currentInstance: NewsBus? = null
        val instance: NewsBus?
            get() {
                if (currentInstance == null) {
                    currentInstance = NewsBus()
                }
                return currentInstance
            }
        fun publish(id: String) {
            if (!readed.contains(id)) {
                readed.add(id)
            }
            publisher.onNext(readed.size.toString())
        }

        fun listen(): Observable<String> {
            return publisher
        }
    }
}
