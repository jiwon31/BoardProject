import { format, register } from "timeago.js";
import koLocale from "timeago.js/lib/lang/ko";

register("ko", koLocale);

export function formatDate(date: string, lang = "ko"): string {
  return format(date, lang);
}