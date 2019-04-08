import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../modules/models/user';
import {Subscript} from '../modules/models/subscript';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private path: string = '/api/users';


  constructor(private http: HttpClient) {
  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.path);
  }

  public deleteUser(userId: number): Observable<void>{
    return this.http.delete<void>(this.path + '/' + userId);
  }

  public saveUser(user: User): Observable<User>{
    return this.http.post<User>(this.path, user);
  }

  public updateUsersLogin(user: User): Observable<User>{
    return this.http.put<User>(this.path + '/login', user);
  }

  public updateUsersPassword(user: User): Observable<User>{
    return this.http.put<User>(this.path + '/password', user);
  }

  public updateUsersEmail(user: User): Observable<User>{
    return this.http.put<User>(this.path + '/email', user);
  }

  public getLoginUser(user: User): Observable<User>{
    return this.http.post<User>(this.path + '/authorization', user);
  }

  public saveUsersImage(image: File, userId: number): Observable<User> {
    const formData = new FormData();
    formData.append('userImage', image, image.name);
    return this.http.post<User>(this.path + '/image/' + userId, formData);
  }

}
