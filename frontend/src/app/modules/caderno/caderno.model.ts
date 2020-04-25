import { Resource } from 'src/app/resource.model';
import { Nota } from '../nota/nota.model';

export class Caderno extends Resource {
  nome: string;
  descricao: string;
  notas: Nota[];
}