package webcrawler

class Row {

    String competencia
    String publicacao
    String inicioVigencia

    Row(String competencia, String publicacao, String inicioVigencia) {

        this.competencia = competencia
        this.publicacao = publicacao
        this.inicioVigencia = inicioVigencia

    }

    @Override
    String toString() {
        return "Competencia: $competencia\tPublicação: $publicacao\tInicio Vigencia: $inicioVigencia"
    }
}
