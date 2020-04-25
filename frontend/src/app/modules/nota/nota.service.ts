import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ResourceService } from 'src/app/resource.service';
import { Nota } from './nota.model';

@Injectable({
  providedIn: 'root'
})
export class NotaService extends ResourceService<Nota>{

  constructor(
    private http: HttpClient,
    @Inject('defaultURL') defaultUrl: string
  ) {
    super(http, defaultUrl, 'notas');
  }
}
