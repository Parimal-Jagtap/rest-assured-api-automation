# Contributing to REST Assured API Framework

## Where Things Go
New API wrapper?       → src/test/java/api/
New test class?        → src/test/java/tests/
New utility?           → src/test/java/utils/
New test data?         → test-data/api-test-data.json
Config changes?        → src/test/resources/config/

## Commit Format
feat: add UserAPI wrapper for user management endpoints
test: add negative tests for expired token handling
fix: resolve JSON parsing error in OrderAPITest
docs: add Postman collection link to README
refactor: move auth header logic to BaseAPITest
ci: add parallel test execution to GitHub Actions

## API Test Rules

- Every endpoint needs both positive and negative tests
- Always validate: status code + response body + response time
- Use ResponseValidator utility — never assert inline
- No hardcoded URLs — always use config properties
- Token handling in BaseAPITest only
- Test data in JSON files only — no hardcoded values in tests

## Response Validation Standard

Every test must validate minimum:
1. Status code
2. At least one body field
3. Response time below 3000ms
