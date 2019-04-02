export class Subscript {
  id: number;
  name: string;
  description: string;
  price: number;
  period: number;
  imagePath: string;


static cloneSubscript(subscript: Subscript): Subscript {
  const clonedSubscript: Subscript = new Subscript();
  clonedSubscript.id = subscript.id;
  clonedSubscript.name = subscript.name;
  clonedSubscript.description = subscript.description;
  clonedSubscript.price = subscript.price;
  clonedSubscript.period = subscript.period;
  clonedSubscript.imagePath = subscript.imagePath;
  return clonedSubscript;
}

}
