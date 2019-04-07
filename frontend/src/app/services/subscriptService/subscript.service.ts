import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Subscript} from '../../modules/models/subscript';

@Injectable({
  providedIn: 'root'
})
export class SubscriptService {

  private path: string = '/api/subscripts';


  constructor(private http: HttpClient) {
  }

  public getSubscripts(): Observable<Subscript[]> {
    return this.http.get<Subscript[]>(this.path);
  }

  public saveSubscript(subscript: Subscript): Observable<Subscript> {
    return this.http.post<Subscript>(this.path, subscript);
  }

  public deleteSubscript(subscriptId: number): Observable<void> {
    return this.http.delete<void>(this.path + '/' + subscriptId);
  }

  public saveSubscriptsImage(image: File, subscriptId: number): Observable<void> {
    const formData = new FormData();
    formData.append('subscriptImage', image, image.name);
    return this.http.post<void>(this.path + '/image/' + subscriptId, formData);
  }


}
