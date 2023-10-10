package io.github.nicholassiew1991.urlshortenerapi.tasks.publishers

interface TaskPublisher {

  fun publish(taskName: String, data: Any)

}