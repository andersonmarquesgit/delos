-- Inserts de TB_EMPRESA --
INSERT INTO tb_company(id, cnpj, name)
    VALUES (1, '56.212.211/0001-42', 'Qualyes');
    
-- Inserts de TB_UNIDADE --
INSERT INTO tb_unit(id, name, fk_company)
    VALUES (1, 'Unidade SP', 1);
    
-- Inserts de TB_NIVEL_USUARIO --
INSERT INTO tb_user_level(id, dt_change, dt_inclusion, name, critical_analisys_permission, configuration_permission, doc_permission, sac_permission)
    VALUES (1, null, current_timestamp, 'Administrador', true, true, true, true);
INSERT INTO tb_user_level(id, dt_change, dt_inclusion, name, critical_analisys_permission, configuration_permission, doc_permission, sac_permission)
    VALUES (2, null, current_timestamp, 'Usuário', true, false, true, true);
    
INSERT INTO tb_role (id, name) 
	VALUES (1, 'ADMIN');
INSERT INTO tb_role (id, name) 
	VALUES (2, 'USER');	

-- Inserts de TB_USUARIO --
INSERT INTO tb_user(id, active, email, login, name, password, fk_company, fk_user_level, fk_unit)
    VALUES (1, true, 'admin@qualyes.com.br', 'admin', 'Administrador', 'admin@123', 1, 1, 1);   
INSERT INTO tb_user(id, active, email, login, name, password, fk_company, fk_user_level, fk_unit)
    VALUES (2, true, 'user@qualyes.com.br', 'user', 'Usuário Qualyes', 'user@123', 1, 2, 1);   
    
INSERT INTO tb_user_role(id_user, id_role)
	VALUES (1, 1);
    
-- Inserts de tb_factor --
INSERT INTO tb_factor(id, description)
    VALUES (1, 'Sac');
INSERT INTO tb_factor(id, description)
    VALUES (2, 'Análise Crítica');
INSERT INTO tb_factor(id, description)
    VALUES (3, 'Auditoria Interna');
INSERT INTO tb_factor(id, description)
    VALUES (4, 'Não Conformidade');
INSERT INTO tb_factor(id, description)
    VALUES (5, 'Plano de Melhoria');
INSERT INTO tb_factor(id, description)
    VALUES (6, 'Produtos e Fornecedores');
INSERT INTO tb_factor(id, description)
    VALUES (7, 'Calibração');
INSERT INTO tb_factor(id, description)
    VALUES (8, 'Recursos Humanos');
    
-- Inserts de tb_document_type --
INSERT INTO tb_document_type(id, description)
    VALUES (1, 'Procedimento');
INSERT INTO tb_document_type(id, description)
    VALUES (2, 'Política de Tratamento de Reclamações');
INSERT INTO tb_document_type(id, description)
    VALUES (3, 'Treinamento');
INSERT INTO tb_document_type(id, description)
    VALUES (4, 'Designação de Pessoas');
INSERT INTO tb_document_type(id, description)
    VALUES (5, 'Documentos Complementares');

-- Inserts de tb_reclamation_type
INSERT INTO tb_reclamation_type(id, description)
    VALUES (1, 'Problema com produto');
INSERT INTO tb_reclamation_type(id, description)
    VALUES (2, 'Problema com a utilização do produto');
INSERT INTO tb_reclamation_type(id, description)
    VALUES (3, 'Atendimento recebido');
    
-- Inserts de tb_reclamation_status
INSERT INTO tb_reclamation_status(id, description)
    VALUES (1, 'Análise da gravidade');
INSERT INTO tb_reclamation_status(id, description)
    VALUES (2, 'Análise de ação');
INSERT INTO tb_reclamation_status(id, description)
    VALUES (3, 'Aceite do cliente');
INSERT INTO tb_reclamation_status(id, description)
    VALUES (4, 'Concluída');  
    
-- Inserts de tb_gravity
INSERT INTO tb_gravity(id, description)
    VALUES (1, 'Baixa');
INSERT INTO tb_gravity(id, description)
	VALUES (2, 'Média');
INSERT INTO tb_gravity(id, description)
	VALUES (3, 'Alta');  
	
-- Inserts de tb_complexity
INSERT INTO tb_complexity(id, description)
    VALUES (1, 'Baixa');
INSERT INTO tb_complexity(id, description)
	VALUES (2, 'Média');
INSERT INTO tb_complexity(id, description)
	VALUES (3, 'Alta'); 
	
-- Inserts de tb_result
INSERT INTO tb_result(id, description)
    VALUES (1, 'Sim');
INSERT INTO tb_result(id, description)
	VALUES (2, 'Não');
INSERT INTO tb_result(id, description)
	VALUES (3, 'Não Aplicável'); 
	
-- Inserts de tb_conclusion
INSERT INTO tb_conclusion(id, description)
    VALUES (1, 'Satisfatório');
INSERT INTO tb_conclusion(id, description)
	VALUES (2, 'Não satisfatório');

-- Inserts de tb_section
INSERT INTO tb_section(id, description)
    VALUES (1, 'Mudanças que possam afetar o Sistema de Gestão da Qualidade');
INSERT INTO tb_section(id, description)
	VALUES (2, 'Recomendações para Melhorias');
INSERT INTO tb_section(id, description)
	VALUES (3, 'Política e Objetivos da Qualidade');
INSERT INTO tb_section(id, description)
	VALUES (4, 'Auditorias');
INSERT INTO tb_section(id, description)
	VALUES (5, 'Reclamações de Clientes');
INSERT INTO tb_section(id, description)
	VALUES (6, 'Ações Corretivas e Preventivas');
INSERT INTO tb_section(id, description)
	VALUES (7, 'Acompanhamento de ações oriundas de análises críticas anteriores');
	
-- Inserts de TB_ITEM
INSERT INTO tb_item(id, description, fk_section)
    VALUES (1, 'É verificada a eficácia da aplicação e entendimento da documentação do SGQ (Manual da Qualidade, Procedimentos, Instruções de Trabalho e Registros / Formulários), analisando a sua estrutura e sugerindo mudanças, quando necessário?', 1);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (2, 'São verificadas as necessidades de adequações no SGQ, analisando as mudanças que, por ventura, tenham ocorrido na empresa?', 1);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (3, 'São analisadas recomendações de melhorias sugeridas pelos colaboradores?', 2); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (4, 'São estabelecidas sempre que necessárias mudanças no Sistema da Qualidade, inclusive na política e objetivos da Qualidade e nos indicadores de desempenho, visando melhoria contínua deste sistema?', 2);	
INSERT INTO tb_item(id, description, fk_section)
	VALUES (5, 'A Política da Qualidade, os objetivos da qualidade e desdobramentos estão adequados às metas organizacionais da empresa e direcionados para o atendimento das expectativas necessidades de nossos clientes?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (6, 'A Política da Qualidade está estabelecida e documentada, compreendida, implementada e mantida em todos os níveis da organização?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (7, 'O sistema de gestão da qualidade, considerando todas as divisões do escopo certificado ou objeto de certificação, está adequado e eficaz em relação à Política da Qualidade e seus objetivos?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (8, 'O desempenho da organização, em relação às metas organizacionais estabelecidas nos indicadores, é satisfatório?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (9, 'É necessário revisar os objetivos da qualidade e seus desdobramentos em relação às metas organizacionais ou necessidades e expectativas de nossos clientes?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (10, 'O SGQ está adequado e eficaz em relação à Política de Gestão da empresa?', 3); 
INSERT INTO tb_item(id, description, fk_section)
	VALUES (11, 'Os resultados da última Auditoria da Qualidade são satisfatórios?', 4);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (12, 'Quando da realização de Auditoria, por parte do cliente, os resultados são satisfatórios?', 4);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (13, 'O Sistema de Gestão da Qualidade está conforme com os requisitos das Normas NBR ISO 9001:2008 estabelecidos pela organização?', 4);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (14, 'O Sistema de Gestão da Qualidade está mantido e implementado eficazmente?', 4);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (15, 'O Planejamento da realização do produto está conforme com os requisitos do sistema de gestão da qualidade estabelecidos pela organização?', 4);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (16, 'Os indicadores de desempenho relacionados ao produto e atendimento, se apresentam em níveis satisfatórios?', 5);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (17, 'A organização planeja o lançamento de novos serviços e/ou melhoria nos serviços atuais?', 5);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (18, 'As ações corretivas e preventivas têm contribuído para a melhoria da eficácia do sistema de gestão da qualidade implementado?', 6);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (19, 'Os resultados das ações corretivas e preventivas executadas são satisfatórios?', 6);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (20, 'As quantidades de ações corretivas e preventivas executadas são satisfatórias?', 6);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (21, 'As ações oriundas de análises críticas anteriores foram ou estão sendo executadas conforme o prazo determinado?', 7);
INSERT INTO tb_item(id, description, fk_section)
	VALUES (22, 'As ações oriundas de análises críticas anteriores, já executadas, foram eficazes?', 7);