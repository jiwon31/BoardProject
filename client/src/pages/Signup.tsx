import { Button } from "components/ui/Button";
import useUser from "hooks/useUser";
import { useState } from "react";
import { SignUpRequest } from "types/auth";

const initialSignUpInfo: SignUpRequest = {
  email: "",
  password: "",
  userName: "",
};

export default function Signup() {
  const [signUpinfo, setSignUpInfo] =
    useState<SignUpRequest>(initialSignUpInfo);
  // const [disabled, setDisabled] = useState<boolean>(true);
  const [emailDuplicateMessage, setEmailDuplicateMessage] = useState<
    string | null
  >(null);
  const [nameDuplicateMessage, setNameDuplicateMessage] = useState<
    string | null
  >(null);
  const { checkEmailDuplicate, checkUserNameDuplicate } = useUser();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSignUpInfo((prev) => ({ ...prev, [name]: value }));
  };

  const handleCheckEmailDuplicate = () => {
    checkEmailDuplicate.mutate(signUpinfo.email, {
      onSuccess: (message) => {
        setEmailDuplicateMessage(message);
        setTimeout(() => setEmailDuplicateMessage(null), 3000);
      },
      onError: (error) => alert(error.message),
    });
  };

  const handleCheckUserNameDuplicate = () => {
    checkUserNameDuplicate.mutate(signUpinfo.userName, {
      onSuccess: (message) => {
        setNameDuplicateMessage(message);
        setTimeout(() => setNameDuplicateMessage(null), 3000);
      },
    });
  };

  return (
    <section className="flex flex-col items-center gap-y-6 py-10 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold">회원가입</h1>
      <form
        className="flex flex-col gap-y-3 w-7/12 py-10"
        onSubmit={handleSubmit}
      >
        <div className="flex flex-col gap-y-3 mb-10">
          <div className="flex justify-between">
            <input
              className="flex-1 mr-0.5"
              type="email"
              name="email"
              value={signUpinfo.email}
              placeholder="이메일을 입력하세요"
              required
              onChange={handleChange}
            />
            <Button
              text="중복 확인"
              disabled={!signUpinfo.email}
              onClick={handleCheckEmailDuplicate}
            />
          </div>
          {emailDuplicateMessage && (
            <p className="pb-1 text-sm text-green-500">
              {emailDuplicateMessage}
            </p>
          )}
          <input
            type="password"
            name="password"
            value={signUpinfo.password}
            placeholder="비밀번호를 입력하세요"
            required
            onChange={handleChange}
          />
          <div className="flex justify-between">
            <input
              className="flex-1 mr-0.5"
              type="userName"
              name="userName"
              value={signUpinfo.userName}
              placeholder="닉네임을 입력하세요"
              required
              onChange={handleChange}
            />
            <Button
              text="중복 확인"
              disabled={!signUpinfo.userName}
              onClick={handleCheckUserNameDuplicate}
            />
          </div>
          {nameDuplicateMessage && (
            <p className="pb-1 text-sm text-green-500">
              {nameDuplicateMessage}
            </p>
          )}
        </div>
        <Button
          text="회원가입하기"
          disabled={Object.values(signUpinfo).some((info) => !info)}
          type="submit"
        />
      </form>
    </section>
  );
}
