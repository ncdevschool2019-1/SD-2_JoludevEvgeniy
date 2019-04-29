import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {User} from '../modules/models/user';
import {ChangeParamsUser} from '../modules/models/change-params-user';
import {AuthRegUser} from '../modules/models/auth-reg-user';
import {AuthToken} from '../modules/models/auth-token';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private path: string = '/api/users';

  constructor(private http: HttpClient) {
  }

  public getMaxPage(): Observable<number>{
    return this.http.get<number>(this.path + "/maxPage");
  }

  public getUsers(pageNumber: number): Observable<User[]> {
    return this.http.get<User[]>(this.path + "/page/" + pageNumber);
  }

  public deleteUser(userId: number): Observable<void>{
    return this.http.delete<void>(this.path + '/' + userId);
  }

  public saveUser(user: AuthRegUser): Observable<User>{
    return this.http.post<User>(this.path, user);
  }

  public updateUsersLogin(changePasswordUser: ChangeParamsUser): Observable<AuthToken>{
    return this.http.post<AuthToken>(this.path + '/login', changePasswordUser);
  }

  public updateUsersPassword(changePasswordUser: ChangeParamsUser): Observable<AuthToken>{
    return this.http.post<AuthToken>(this.path + '/password', changePasswordUser);
  }

  public updateUsersEmail(changePasswordUser: ChangeParamsUser): Observable<AuthToken>{
    return this.http.post<AuthToken>(this.path + '/email', changePasswordUser);
  }

  public saveUsersImage(image: File, userId: number): Observable<User> {
    const formData = new FormData();
    formData.append('userImage', image, image.name);
    return this.http.post<User>(this.path + '/image/' + userId, formData);
  }

  public getLoggedUser(login: string): Observable<User>{
    return this.http.post<User>(this.path + "/logged", login);
  }

}
