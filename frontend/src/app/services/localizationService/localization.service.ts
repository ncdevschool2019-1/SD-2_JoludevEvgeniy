import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LocalizationService {

  public localizations: any[];
  public currentLocalization: any;

  constructor(private http: HttpClient) {
    this.getLocalizations().subscribe(data => {
      this.localizations = data;
      this.currentLocalization = this.localizations[0];
    });

  }localization

  public getLocalizations(): Observable<any[]> {
    return this.http.get<any[]>('../../../assets/localization.json');
  }
}
