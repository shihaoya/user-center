/**
 * @see https://umijs.org/docs/max/access#access
 * */
import {ROLE_ENUM} from "@/enums";

export default function access(
  initialState: { currentUser?: API.CurrentUser } | undefined,
) {
  const { currentUser } = initialState ?? {};
  return {
    canAdmin: currentUser && currentUser.role === ROLE_ENUM.ADMIN,
  };
}
