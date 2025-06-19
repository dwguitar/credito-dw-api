export interface Credito {
    id?: number;
    numeroCredito: string;
    numeroNfse: string;
    dataConstituicao: Date;
    valorIssqn: number;
    tipoCredito: string;
    simplesNacional: boolean;
    aliquota: number;
    valorFaturado: number;
    valorDeducao: number;
    baseCalculo: number;
  }

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;  // Número da página atual
  size: number;    // Tamanho da página
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

