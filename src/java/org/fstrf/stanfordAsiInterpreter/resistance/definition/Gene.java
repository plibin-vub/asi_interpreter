/**
Copyright 2017 Frontier Science & Technology Research Foundation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

ADDITIONAL DISCLAIMER:
In addition to the standard warranty exclusions and limitations of 
liability set forth in sections 7, 8 and 9 of the Apache 2.0 license 
that governs the use and development of this software, Frontier Science 
& Technology Research Foundation disclaims any liability for use of 
this software for patient care or in clinical settings. This software 
was developed solely for use in medical and public health research, and 
was not intended, designed, or validated to guide patient care.
*/ 



package org.fstrf.stanfordAsiInterpreter.resistance.definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fstrf.stanfordAsiInterpreter.resistance.ASIEvaluationException;
import org.fstrf.stanfordAsiInterpreter.resistance.evaluate.EvaluatedGene;
import org.fstrf.stanfordAsiInterpreter.resistance.grammar.MutationComparator;

public class Gene {

	private String name;
	/*
	 * drugClassList of DrugClass objects
	 */
	private Set drugClasses;
	private List geneRules;
	
	public Gene(String name, Set drugClasses, List geneRules) {
		this.name = name;
		this.drugClasses = drugClasses;
		this.geneRules = geneRules;
	}
	
	public Gene(String name, Set drugClasses) {
		this(name, drugClasses, new ArrayList());
	}
	
	public Gene(String name, List geneRules) {
		this(name, new HashSet(), geneRules);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Set getDrugClasses() {
		return this.drugClasses;
	}
	
	public List getRules() {
		return this.geneRules;
	}
	
	public String toString() {
		return this.name;
	}
	
	public EvaluatedGene evaluate(List mutations, MutationComparator comparator) throws ASIEvaluationException {
		Collection evaluatedGeneRules = new ArrayList();	
		for(Iterator iter = this.geneRules.iterator(); iter.hasNext();) {
			Rule geneRule = (Rule) iter.next();
			evaluatedGeneRules.add(geneRule.evaluate(mutations, comparator));
		}
		
		Collection evaluatedDrugClasses = new ArrayList();
		for(Iterator iter = this.drugClasses.iterator(); iter.hasNext();) {
			DrugClass drugClass = (DrugClass) iter.next();
			evaluatedDrugClasses.add(drugClass.evaluate(mutations, comparator));
		}
		
		return new EvaluatedGene(this, evaluatedGeneRules, evaluatedDrugClasses);
	}
}
