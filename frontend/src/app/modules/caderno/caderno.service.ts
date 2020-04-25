import { Injectable, Inject } from '@angular/core';
import { Caderno } from './caderno.model';
import { ResourceService } from 'src/app/resource.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CadernoService extends ResourceService<Caderno> {

  constructor(
    private http: HttpClient,
    @Inject('defaultURL') defaultUrl: string
  ) {
    super(http, defaultUrl, 'cadernos');
  }
}
