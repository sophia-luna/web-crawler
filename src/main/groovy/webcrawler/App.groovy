/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package webcrawler
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class App {

    static void main(String[] args) {

        Task1.downloadComponenteComunicacao()
        Task2.coletarDados()
        Task3.downloadTabelaErros()

    }
}

