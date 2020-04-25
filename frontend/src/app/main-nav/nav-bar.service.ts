import { Injectable } from '@angular/core';
import { Caderno } from '../modules/caderno/caderno.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavBarService {

  cadernoSelecionado: Caderno;
  cadernoSelecionado$ = new Observable<Caderno>(obs => {
    let cadernoAtual = this.cadernoSelecionado;
    setInterval(() => {
      if (cadernoAtual !== this.cadernoSelecionado) {
        cadernoAtual = this.cadernoSelecionado;
        obs.next(this.cadernoSelecionado);
      }
    }, 50);
  });

  constructor() { }
}
