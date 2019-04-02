import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActiveSubscript} from '../../modules/models/active-subscript';
import {Observable} from 'rxjs';

@Injectable({
  providedIn:'root'
})
export class ActiveSubscriptService{

  constructor(private http: HttpClient){

  }

  public saveActiveSubscript(activeSubscript: ActiveSubscript): Observable<ActiveSubscript>{
    return this.http.post<ActiveSubscript>('/api/active-subscripts', activeSubscript);
  }

  public deleteActiveSubscript(activeSubscriptId: number): Observable<void> {
    return this.http.delete<void>('/api/active-subscripts/' + activeSubscriptId.toString());
  }
}
