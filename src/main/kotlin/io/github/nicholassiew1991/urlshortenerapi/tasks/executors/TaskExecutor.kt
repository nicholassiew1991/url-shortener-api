package io.github.nicholassiew1991.urlshortenerapi.tasks.executors

interface TaskExecutor {

  fun execute(data: String)

}