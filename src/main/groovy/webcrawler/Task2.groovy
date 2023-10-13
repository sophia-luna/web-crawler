package webcrawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class Task2 {

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

    static void acessarHistorico(){

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

            //buscar historico
            url = buscarElemento('a:contains(Clique aqui para acessar todas as versões dos Componentes)')

            //acessar historico
            acessarSite(url)


        } catch (Exception e) {

            e.printStackTrace()

        }
    }

    static void coletarDados() {

        try {

            acessarHistorico()

            LinkedList<Row> rows = new LinkedList<Row>()
            boolean data = true

            String competencia, publicacao, inicioVigencia

            Element tabela = document.select("table").first()
            tabela.select("tr").each { linha ->
                Elements colunas = linha.select("td")

                if (colunas.size() >= 3) {
                    competencia = colunas[0].text().trim()
                    publicacao = colunas[1].text().trim()
                    inicioVigencia = colunas[2].text().trim()
                }

                if (data && competencia && publicacao && inicioVigencia) {
                    rows.add(new Row(competencia, publicacao, inicioVigencia))
                }

                if (competencia == 'Jan/2016') {
                    data = false
                }
            }

            salvarDados(rows)

        } catch (Exception e) {
            e.printStackTrace()
        }
    }


    static void salvarDados(LinkedList<Row> rows){

        File file = new File('downloads/Historico.txt')
        file.parentFile.mkdirs()

        file.withWriter { writer ->
            rows.each { historico ->
                writer.write(historico.toString() + '\n')
            }
        }

        println("Historico salvo em 'downloads/Historico.txt'.")
    }
}