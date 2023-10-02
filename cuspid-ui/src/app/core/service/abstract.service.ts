import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';

enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE'
}

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractService<T> implements HttpInterceptor {
  ENTITY_URL: string = '';
  protected readonly httpClient: HttpClient;
  protected readonly router: Router;

  protected constructor(
    private readonly injector: Injector
  ) {
    this.httpClient = injector.get(HttpClient);
    this.router = injector.get(Router);
  }

  get(url: string): Observable<T> {
    return this.httpClient.get<T>(url);
  }

  post(url: string, body?: any): Observable<T> {
    return this.httpClient.post<T>(url, body);
  }

  put(url: string, body?: any): Observable<T> {
    return this.httpClient.put<T>(url, body);
  }

  delete(url: string): Observable<T> {
    return this.httpClient.delete<T>(url);
  }

  request<OT>(httpMethod: HttpMethod, url: string, body?: any): Observable<OT> {
    return this.httpClient.request<any>(httpMethod, url, {
      body
    });
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return new Observable<HttpEvent<any>>();
  }

  getEntities(): Observable<T[]> {
    return this.request<T[]>(HttpMethod.GET, `${this.ENTITY_URL}`);
  }

  getEntityById(id: string): Observable<T> {
    return this.get(`${this.ENTITY_URL}/${id}`);
  }

  createEntity(entity: T): Observable<T> {
    return this.post(`${this.ENTITY_URL}`, entity);
  }

  createEntities(entities: T[]): Observable<T[]> {
    return this.request<T[]>(HttpMethod.POST, `${this.ENTITY_URL}`, entities);
  }

  updateEntity(entity: T): Observable<T> {
    return this.put(`${this.ENTITY_URL}}`, entity);
  }

  updateEntities(entities: T[]): Observable<T[]> {
    return this.request<T[]>(HttpMethod.PUT, `${this.ENTITY_URL}`, entities);
  }

  deleteEntityById(id: string): Observable<T> {
    return this.delete(`${this.ENTITY_URL}/${id}`);
  }

}
