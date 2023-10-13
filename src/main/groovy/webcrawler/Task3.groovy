package webcrawler

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class Task3 {

    static Document document

    static void downloadTabelaErros() {

        try{

            acessarTabelaErros()

            //download tabelas
            Element downloadLink = document.select("a:contains(Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx))").first()

            if (downloadLink) {

                def fileUrl = downloadLink.attr("href")
                def fileName = "Tabela_de_erros_no_envio_para_a_ANS.zip"
                def downloadDirectory = "downloads"

                def dir = new File(downloadDirectory)
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                //conexão HTTP para baixar o arquivo
                Connection connection = Jsoup.connect(fileUrl) as Connection
                def response = connection.ignoreContentType(true).execute()

                if (response.statusCode() == 200) {
                    def fileBytes = response.bodyAsBytes()
                    def targetFile = new File(downloadDirectory, fileName)
                    def outputStream = new FileOutputStream(targetFile)
                    outputStream.write(fileBytes)
                    outputStream.close()

                    println "Arquivo baixado com sucesso em $targetFile"
                } else {
                    println "Erro ao acessar o arquivo. Código de status: ${response.statusCode()}"
                }
            } else {
                println "Nenhum link de download encontrado com a extensão especificada."
            }

        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    static void acessarTabelaErros() {

        try {

            //acessar site do governo
            String url = 'https://www.gov.br/ans/pt-br'
            acessarSite(url)

            //buscar espaço do prestador de serviços de saúde
            url = buscarElemento('a:contains(Espaço do Prestador de Serviços de Saúde)')

            //acessar site do prestador de serviços de saúde
            acessarSite(url)

            //buscar tiss
            url = buscarElemento('a:contains(TISS - Padrão para Troca de Informação de Saúde Suplementar)')

            //acessar tiss
            acessarSite(url)

            //buscar tabelas relacionadas
            url = buscarElemento('a:contains(Clique aqui para acessar as planilhas)')

            //acessa tabelas relacionadas
            acessarSite(url)

        } catch (Exception e) {

            e.printStackTrace()

        }
    }

    static void acessarSite(String url){

        document = Jsoup.connect(url).get()

    }

    static String buscarElemento(String busca){

        Element link = document.select(busca).first()

        if(link) {

            String href = link.attr('href')
            println 'Link encontrado: ' + href
            return href

        } else {

            println busca + 'nao encontrado.'
            return ""

        }

    }
}