import { Button } from "components/ui/Button";
import useAuth from "hooks/useAuth";
import useUser from "hooks/useUser";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { SignUpRequest } from "types/auth";

const initialSignUpInfo: SignUpRequest = {
  email: "",
  password: "",
  userName: "",
};

export default function Signup() {
  const [signUpinfo, setSignUpInfo] =
    useState<SignUpRequest>(initialSignUpInfo);
  const [isEmailDuplicated, setIsEmailDuplicated] = useState<boolean>();
  const [emailDuplicateMessage, setEmailDuplicateMessage] = useState<string>();
  const [isNameDuplicated, setIsNameDuplicated] = useState<boolean>();
  const [nameDuplicateMessage, setNameDuplicateMessage] = useState<string>();
  const [isValidPassword, setIsValidPassword] = useState<boolean>(false);
  const isSignUpButtonDisabled =
    isEmailDuplicated === false &&
    isValidPassword === true &&
    isNameDuplicated === false;

  const { checkEmailDuplicate, checkUserNameDuplicate } = useUser();
  const { signUp } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    signUp.mutate(signUpinfo, {
      onSuccess: (message) => {
        alert(message);
        navigate("/login");
      },
      onError: (error) => alert(error.message),
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSignUpInfo((prev) => ({ ...prev, [name]: value }));

    if (name === "password") {
      const regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[$@$!%*#?&]).{8,}$/;
      setIsValidPassword(regExp.test(value));
    }
  };

  const handleCheckEmailDuplicate = () => {
    checkEmailDuplicate.mutate(signUpinfo.email, {
      onSuccess: (message) => {
        setIsEmailDuplicated(false);
        setEmailDuplicateMessage(message);
      },
      onError: (error) => {
        setIsEmailDuplicated(true);
        setEmailDuplicateMessage(error.message);
      },
      onSettled: () =>
        setTimeout(() => setEmailDuplicateMessage(undefined), 3000),
    });
  };

  const handleCheckUserNameDuplicate = () => {
    checkUserNameDuplicate.mutate(signUpinfo.userName, {
      onSuccess: (message) => {
        setIsNameDuplicated(false);
        setNameDuplicateMessage(message);
      },
      onError: (error) => {
        setIsNameDuplicated(true);
        setNameDuplicateMessage(error.message);
      },
      onSettled: () =>
        setTimeout(() => setNameDuplicateMessage(undefined), 3000),
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
              onClick={handleCheckEmailDuplicate}
              disabled={!signUpinfo.email}
            />
          </div>
          {emailDuplicateMessage && (
            <p
              className={`${
                isEmailDuplicated ? "text-red-500" : "text-green-500"
              } text-sm`}
            >
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
          {signUpinfo.password && !isValidPassword && (
            <p className="text-sm text-red-500">
              비밀번호는 최소 8자리에 숫자, 문자, 특수문자가 각 1개 이상
              포함되어야 합니다.
            </p>
          )}
          <div className="flex justify-between">
            <input
              className="flex-1 mr-0.5"
              name="userName"
              value={signUpinfo.userName}
              placeholder="닉네임을 입력하세요"
              required
              onChange={handleChange}
            />
            <Button
              text="중복 확인"
              onClick={handleCheckUserNameDuplicate}
              disabled={!signUpinfo.userName}
            />
          </div>
          {nameDuplicateMessage && (
            <p
              className={`${
                isNameDuplicated ? "text-red-500" : "text-green-500"
              } text-sm`}
            >
              {nameDuplicateMessage}
            </p>
          )}
        </div>
        <Button
          text="회원가입하기"
          disabled={!isSignUpButtonDisabled}
          type="submit"
          isLoading={signUp.isLoading}
        />
      </form>
    </section>
  );
}
