# Moodiary — Terceira VA (Individual)

Aplicação full-stack com **Angular + JWT** (frontend) e **Spring Boot + JWT** (backend).  
Permite cadastro/login, criação de entradas de diário com humor opcional e busca/filtragem.

## ✅ URLs em Produção
- **Frontend (Render):** https://moodiary-frontend.onrender.com
- **Backend (Render – API base):** https://moodiary-umse.onrender.com  
  - Health: `/actuator/health`, `/actuator/info`, `/_ping`

> **CORS:** backend liberado para o domínio do frontend.

---

## 👤 Como Acessar / Usar
1. Acesse o **Frontend**: https://moodiary-frontend.onrender.com  
2. **Cadastrar**: informe e-mail válido, senha forte (8+ com maiúscula/minúscula/número/símbolo) e nome completo.  
3. **Login**: use o e-mail/senha cadastrados (o frontend guarda o JWT).  
4. **Diário**:
   - **Nova entrada**: conteúdo (obrigatório), **humor 1..5 (opcional)**, “Salvar como rascunho”.  
   - **Listar/Buscar**: palavra-chave ou filtros de **ano/mês/dia**.  
   - **Logout**: encerra a sessão e bloqueia as rotas protegidas.

> As datas de criação/atualização são **automatizadas** no backend.

---

## 📌 Histórias de Usuário (implementação e como testar)
1) **Cadastro de Usuário**  
   - Validações: e-mail, senha forte, nome obrigatório; e-mail único.  
   - Teste: tela de cadastro → mensagem de sucesso → redireciona ao login.

2) **Autenticação (Login)**  
   - Retorna **JWT**; erro genérico se credenciais inválidas.  
   - Teste: faça login e verifique acesso às rotas protegidas.

3) **Logout**  
   - Botão “Sair” limpa JWT e redireciona para login; rotas protegidas bloqueiam acesso.  

4) **Criação de Entrada de Diário**  
   - Conteúdo obrigatório (até 5000+), humor opcional, rascunho; data/hora automáticas.  
   - Teste: criar e ver entrada listada em ordem decrescente de data.

5) **Registro de Humor + Resumo**  
   - Guardado por entrada; endpoint de resumo diário (gráfico/linha no front).  

6) **Busca de Entradas**  
   - Campo de busca por texto + filtros **ano/mês/dia**.  
   - Teste: pesquisar por palavra e por data (ano | ano+mês | ano+mês+dia).

---

## 🔐 Segurança (JWT)
- **Filter** valida o token em todas as rotas protegidas.  
- `SecurityFilterChain` libera **/auth/** e endpoints de saúde; demais exigem JWT.  
- Frontend envia `Authorization: Bearer <token>` em todas as requisições.

---
