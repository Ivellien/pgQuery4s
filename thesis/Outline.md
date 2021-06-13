# Outline
## Introduction
 1. Introduction, motivation
 2. Short intro to what is Scala, PostgreSQL, SQL parse tree etc.
 3. Functional programming, differences, pros

## Theoretical part

 1. Description of internal PostgreSQL query parsing
	 - Lexer (flex), Parser (bison)
 2. Existing libraries - Doobie, Quill, ...
 3. Comparison of those libraries, pros and cons. 
 4. Decided direction of my implementation
 5. What is libpg_query? (maybe more into practical part)

## Practical part - pgquery4s

 1. How does libpg_query work.
	 - Accesing internal PostgreSQL parser
 2. Using C library with Scala. Native code vs. Java byte code.		
	 - What is JNI, `sbt-jni`
 3. Using `circe` for parsing of JSON result from libpg_query. Own scala case class structure for parse tree representation. 
 4. Conversion from parse tree back into SQL query.
 5. Scala custom interpolators. How they work and how they are used in my work. 
	 - Expression and query interpolators.
 6. Scala Macros - how they are used for compile time validation.
	 - What macros are
	 - Lifting
 7. Using interpolators and macros to validate parameterized PostgreSQL queries.
	 - Use of PostgreSQL inner node ParamRef
	 - Transforming the syntax tree
 9. Summary of what is done and tested - how is it tested.
 

## Practical part - testing, deployment

 1. Summary of what is done and tested - how is it tested.
 2. Use of Travis CI 
 3. (Possibilities of testing, if everything had specific types)

## Future work
1. Enrich current coverage of tree nodes.
2. Update to new libpg_query version. 
3. Publish the library for open source contribution (???)
