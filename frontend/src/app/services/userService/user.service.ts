import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../modules/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _selectedUser: User;
  private path: string = '/api/users';


  get selectedUser(): User {
    return this._selectedUser;
  }

  set selectedUser(value: User) {
    this._selectedUser = value;
  }

  constructor(private http: HttpClient) {
  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.path);
  }

  public getUserById(userId: number): Observable<User>{
    return this.http.get<User>(this.path + '/' + userId);
  }

  public deleteUser(userId: number): Observable<void>{
    return this.http.delete<void>(this.path + '/' + userId);
  }

  public saveUser(user: User): Observable<User>{
    return this.http.post<User>(this.path, user);
  }

  public clearSelectedUser(): void {
    this.selectedUser = new User();
  }
}
