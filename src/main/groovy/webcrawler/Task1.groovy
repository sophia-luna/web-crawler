package webcrawler

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class Task1 {

    static Document document

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


    static void downloadComponenteComunicacao(){

        try{

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

            //buscar versao setembro/2023
            url = buscarElemento('a:contains(Clique aqui para acessar a versão Setembro/2023)')

            //acessa versao set/2023
            acessarSite(url)

            //botão de download com base no texto componente de comunicação
            Element downloadButton = document.select("a:contains(Componente de Comunicação)").first()

            if (downloadButton) {

                String fileUrl = downloadButton.attr("href")
                String fileName = "Componente_de_Comunicacao.zip"
                String downloadDirectory = "downloads"

                File dir = new File(downloadDirectory)
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
            }

        }catch (Exception e){

            e.printStackTrace()

        }
    }
}
