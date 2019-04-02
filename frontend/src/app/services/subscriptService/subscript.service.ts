import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Subscript} from '../../modules/models/subscript';

@Injectable({
  providedIn: 'root'
})
export class SubscriptService {

  private _selectedSubscript: Subscript = new Subscript();
  private path: string = '/api/subscripts';

  get selectedSubscript(): Subscript {
    return this._selectedSubscript;
  }

  set selectedSubscript(value: Subscript) {
    this._selectedSubscript = value;
  }

  constructor(private http: HttpClient) {
  }

  public getSubscripts(): Observable<Subscript[]> {
    return this.http.get<Subscript[]>(this.path);
  }

  public saveSubscript(subscript: Subscript): Observable<Subscript>{
    return this.http.post<Subscript>(this.path, subscript);
  }

  public deleteSubscript(subscriptId: number): Observable<void> {
    return this.http.delete<void>(this.path + '/' + subscriptId);
  }

  public clearSelectedSubscript(): void {
    this.selectedSubscript = new Subscript();
  }
}
