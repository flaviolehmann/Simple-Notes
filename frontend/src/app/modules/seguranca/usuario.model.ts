import { Caderno } from '../caderno/caderno.model';

export class Usuario {
  username: string;
  password: string;
  cadernos: Caderno[];
}