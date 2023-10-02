export class StringsUtil {
  public static readonly EMPTY: string = '';
  public static readonly HYPHEN: string = '-';

  public static isUpperCase(str: string): boolean {
    return str === str.toUpperCase();
  }

  public static getRoute(str: string): string {
    let result: string = StringsUtil.EMPTY;
    for (let count: number = 0; count < str.length; count++) {
      const element = str[count];
      if (StringsUtil.isUpperCase(element)) {
        result += (count === 0 ? StringsUtil.EMPTY : StringsUtil.HYPHEN) + element.toLowerCase();
      } else {
        result += element;
      }
    }
    return result;
  }

}
